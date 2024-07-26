<?php
// app/Http/Controllers/AnswerController.php

namespace App\Http\Controllers;
use App\Models\QuestionsMore;
use App\Models\Answer;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class CorrectlyAnswered extends Controller
{
    public function correct()
    {
       $topQuestions=DB::table('answers')
       ->join('questionsmore','answers.question_id','=','questionsmore.question_id')
       ->select('questionsmore.question_id','questionsmore.question','questionsmore.challenge_title','answers.count')

      -> orderBy('answers.count','desc')
      -> take(10)
      ->get();

        return
        view('schools.correct',compact('topQuestions'));
        
    }

  
}
