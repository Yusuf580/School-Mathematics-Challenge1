<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\QuestionsMore;
use Maatwebsite\Excel\Facades\Excel;
use App\Imports\QuestionsMoreImport;

class QuestionsMoreController extends Controller
{
    public function index()
    {
        $questions = QuestionsMore::all();
        return view('questionsmore.index', compact('questions'));
    }

    public function import(Request $request)
    {
        $request->validate([
            'file' => 'required|mimes:xlsx,csv'
        ]);

        Excel::import(new QuestionsMoreImport, $request->file('file'));

        return redirect()->route('questionsmore.index')->with('success', 'Data Imported Successfully');
    }
}
