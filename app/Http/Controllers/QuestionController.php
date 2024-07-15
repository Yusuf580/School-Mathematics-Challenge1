<?php

namespace App\Http\Controllers;

use App\Models\Question;
use App\Models\Challenge;
use Illuminate\Http\Request;
use App\Imports\QuestionsImport;
use Maatwebsite\Excel\Facades\Excel;
use Illuminate\Support\Facades\Log;

class QuestionController extends Controller
{
    // Display a listing of the questions.
    public function index()
    {
        $questions = Question::with('challenge')->get();
        return view('questions.index', compact('questions'));
    }

    // Show the form for creating a new question.
    public function create()
    {
        $challenges = Challenge::all();
        return view('questions.create', compact('challenges'));
    }

    // Store a newly created question in storage.
    public function store(Request $request)
    {
        $request->validate([
            'question' => 'required|string',
            'challenge_title' => 'required|string|exists:challenges,title',
        ]);

        Question::create($request->all());

        return redirect()->route('questions.index')->with('success', 'Question created successfully.');
    }

    // Display the specified question.
    public function show($id)
    {
        $question = Question::with('challenge')->findOrFail($id);
        return view('questions.show', compact('question'));
    }

    // Show the form for editing the specified question.
    public function edit($id)
    {
        $question = Question::findOrFail($id);
        $challenges = Challenge::all();
        return view('questions.edit', compact('question', 'challenges'));
    }

    // Update the specified question in storage.
    public function update(Request $request, $id)
    {
        $request->validate([
            'question' => 'required|string',
            'challenge_title' => 'required|string|exists:challenges,title',
        ]);

        $question = Question::findOrFail($id);
        $question->update($request->all());

        return redirect()->route('questions.index')->with('success', 'Question updated successfully.');
    }

    // Remove the specified question from storage.
    public function destroy($id)
    {
        $question = Question::findOrFail($id);
        $question->delete();

        return redirect()->route('questions.index')->with('success', 'Question deleted successfully.');
    }

    // Import questions from an Excel file.
    public function import(Request $request)
    {
        $request->validate([
            'file' => 'required|file|mimes:xls,xlsx'
        ]);

        try {
            Excel::import(new QuestionsImport, $request->file('file'));

            return redirect()->route('questions.index')->with('success', 'Questions imported successfully.');
        } catch (\Exception $e) {
            Log::error('Error during import: ' . $e->getMessage());
            return redirect()->route('questions.index')->with('error', 'There was an error importing the questions. ' . $e->getMessage());
        }
    }
}
