<?php
namespace App\Http\Controllers;

use App\Models\School;
use App\Models\Participant;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class RankingsController extends Controller
{

    public function rankings()
    {
        $challenges = ['CH001', 'CH002', 'CH003', 'CH004'];
        $results = [];

        foreach ($challenges as $challenge) {
            $scores = DB::table('challenge_records')
                ->join('schools', 'challenge_records.registrationNumber', '=', 'schools.Registration')
                ->select('schools.Registration as registrationNumber', 'schools.Name', 'schools.District',
                    DB::raw("SUM(JSON_EXTRACT(challenge_records.$challenge, '$.score')) as total_score"))
                ->groupBy('schools.Registration', 'schools.Name', 'schools.District')
                ->orderBy('total_score', 'desc')
                ->get();

            foreach ($scores as $score) {
                if (!isset($results[$score->registrationNumber])) {
                    $results[$score->registrationNumber] = [
                        'Name' => $score->Name,
                        'total_score' => 0
                    ];
                }

                $results[$score->registrationNumber]['total_score'] += $score->total_score;
            }
        }

        usort($results, function ($a, $b) {
            return $b['total_score'] <=> $a['total_score'];
        });

        return view('schools.rankings', compact('results'));
    }

}
