<?php

namespace App\Http\Controllers;

use App\Models\School;
use App\Models\participant;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class  SchoolperformanceController extends Controller
{

    public function performance(){
  $challenges=['CH001','CH002','CH003','CH004'];
  $results=[];
  foreach($challenges as $challenge){
    $scores=DB::table('challenge_records')
    ->join('schools','challenge_records.registrationNumber','=',
    'schools.Registration')
    ->select('schools.Registration as registrationNumber','schools.Name','schools.District',
    DB:: raw("SUM(JSON_EXTRACT(challenge_records.$challenge,'$.score')) as total_score"))

    ->groupBy('schools.Registration','schools.Name','schools.District')
    ->orderBy('total_score','desc')
          ->get();

    if($scores->isNotEmpty()){
        $bestSchool=$scores->first();
        $worstSchool=$scores->last();
        $results[]=[
            'challenge'=>$challenge,
            'best'=>$bestSchool,
            'worst'=>$worstSchool,
        ];
    }}
    
  
     return view('schools.performance',compact('results'));
}

}
