<?php

namespace App\Imports;

use App\Models\QuestionsMore;
use Maatwebsite\Excel\Concerns\ToModel;
use Maatwebsite\Excel\Concerns\WithHeadingRow;

class QuestionsMoreImport implements ToModel, WithHeadingRow
{
    public function model(array $row)
    {
        return new QuestionsMore([
            'question_id' => $row['question_id'],
            'question' => $row['question'],
            'challenge_title' => $row['challenge_title']
        ]);
    }
}
