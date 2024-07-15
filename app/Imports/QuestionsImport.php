<?php
namespace App\Imports;

use App\Models\Question;
use App\Models\Challenge;
use Maatwebsite\Excel\Concerns\ToModel;
use Maatwebsite\Excel\Concerns\WithHeadingRow;
use Illuminate\Support\Facades\Log;

class QuestionsImport implements ToModel, WithHeadingRow
{
    public function model(array $row)
    {
        Log::info('Importing row:', $row);

        // Check if all necessary keys are present in the row
        if (!isset($row['question_ID']) || !isset($row['question']) || !isset($row['challenge_title'])) {
            Log::error('Missing necessary columns in the row: ' . json_encode($row));
            return null;
        }

        $challenge = Challenge::where('title', $row['challenge_title'])->first();

        if ($challenge) {
            return new Question([
                'question_ID' => $row['question_ID'],
                'question' => $row['question'],
                'challenge_title' => $challenge->title,
            ]);
        } else {
            Log::error('Challenge not found for title: ' . $row['challenge_title']);
            // Optionally handle this case (e.g., skip row, add default, etc.)
            return null; // Skip the row
        }
    }
}
