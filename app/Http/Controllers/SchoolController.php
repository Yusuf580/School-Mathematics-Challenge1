<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\School;

class SchoolController extends Controller
{
    public function index()
    {
        $schools = School::all();
        return view('schools.index', compact('schools'));
    }

    public function create()
    {
        return view('schools.create');
    }

    public function store(Request $request)
    {
        $data = $request->validate([
            'Name' => 'required|string|max:255',
            'Registration' => 'required|string|max:255',
            'District' => 'required|string|max:255',
            'School_Representative' => 'required|string|max:255',
        ]);

        School::create($data);

        return redirect(route('schools.index'))->with('success', 'School created successfully.');
    }

    public function show(School $school)
    {
        return view('schools.show', compact('school'));
    }

    public function edit(School $school)
    {
        return view('schools.edit', compact('school'));
    }

    public function update(Request $request, School $school)
    {
        $data = $request->validate([
            'Name' => 'required|string|max:255',
            'Registration' => 'required|string|max:255',
            'District' => 'required|string|max:255',
            'School_Representative' => 'required|string|max:255',
        ]);

        $school->update($data);

        return redirect(route('schools.index'))->with('success', 'School updated successfully.');
    }

    public function destroy(School $school)
    {
        $school->delete();

        return redirect(route('schools.index'))->with('success', 'School deleted successfully.');
    }
}

