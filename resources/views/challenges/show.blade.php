<!-- resources/views/challenges/show.blade.php -->
@extends('layouts.main')

@section('title', $challenge->title)

@section('content')
    <h1>{{ $challenge->title }}</h1>

    <div class="card">
        <div class="card-body">
            <h5 class="card-title">Challenge Details</h5>
            <p><strong>Start Date:</strong> {{ $challenge->start_date->format('Y-m-d H:i') }}</p>
            <p><strong>End Date:</strong> {{ $challenge->end_date->format('Y-m-d H:i') }}</p>
            <p><strong>Duration:</strong> {{ $challenge->duration }} minutes</p>
            <p><strong>Number of Questions:</strong> {{ $challenge->question_count }}</p>
            <p><strong>Status:</strong> {{ $challenge->is_active ? 'Active' : 'Inactive' }}</p>
        </div>
    </div>

    <h2 class="mt-4">Questions</h2>
    <ul class="list-group">
        @foreach ($challenge->questions as $question)
            <li class="list-group-item">
                <h5>{{ $question->content }}</h5>
                <p><strong>Answer:</strong> {{ $question->answer }}</p>
                <p><strong>Marks:</strong> {{ $question->marks }}</p>
            </li>
        @endforeach
    </ul>

    <div class="mt-3">
        <a href="{{ route('challenges.edit', $challenge) }}" class="btn btn-warning">Edit Challenge</a>
        <a href="{{ route('challenges.index') }}" class="btn btn-secondary">Back to All Challenges</a>
    </div>
@endsection