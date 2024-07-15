@extends('layouts.main')

@section('title', 'Edit Challenge')

@section('content')
    <h1>Edit Challenge</h1>
    <form action="{{ route('challenges.update', $challenge) }}" method="POST">
        @csrf
        @method('PUT')
        <div>
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" value="{{ $challenge->title }}" required>
        </div>
        <div>
            <label for="start_date">Start Date:</label>
            <input type="datetime-local" id="start_date" name="start_date" value="{{ $challenge->start_date->format('Y-m-d\TH:i') }}" required>
        </div>
        <div>
            <label for="end_date">End Date:</label>
            <input type="datetime-local" id="end_date" name="end_date" value="{{ $challenge->end_date->format('Y-m-d\TH:i') }}" required>
        </div>
        <div>
            <label for="duration">Duration (minutes):</label>
            <input type="number" id="duration" name="duration" value="{{ $challenge->duration }}" required>
        </div>
        <div>
            <label for="question_count">Number of Questions:</label>
            <input type="number" id="question_count" name="question_count" min="1" max="100" value="{{ $challenge->question_count }}" required>
        </div>
        <button type="submit">Update Challenge</button>
    </form>
@endsection