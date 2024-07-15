<!-- resources/views/challenges/index.blade.php -->
@extends('layouts.main')

@section('title', 'All Challenges')

@section('content')
    <h1>All Challenges</h1>

    @if (session('success'))
        <div class="alert alert-success">
            {{ session('success') }}
        </div>
    @endif

    <a href="{{ route('challenges.create') }}" class="btn btn-primary mb-3">Create New Challenge</a>

    <table class="table">
        <thead>
            <tr>
                <th>Title</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Duration</th>
                <th>Questions</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            @foreach ($challenges as $challenge)
                <tr>
                    <td>{{ $challenge->title }}</td>
                    <td>{{ $challenge->start_date->format('Y-m-d H:i') }}</td>
                    <td>{{ $challenge->end_date->format('Y-m-d H:i') }}</td>
                    <td>{{ $challenge->duration }} minutes</td>
                    <td>{{ $challenge->question_count }}</td>
                    <td>{{ $challenge->is_active ? 'Active' : 'Inactive' }}</td>
                    <td>
                        <a href="{{ route('challenges.show', $challenge) }}" class="btn btn-sm btn-info">View</a>
                        <a href="{{ route('challenges.edit', $challenge) }}" class="btn btn-sm btn-warning">Edit</a>
                        <form action="{{ route('challenges.destroy', $challenge) }}" method="POST" style="display: inline-block;">
                            @csrf
                            @method('DELETE')
                            <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this challenge?')">Delete</button>
                        </form>
                    </td>
                </tr>
            @endforeach
        </tbody>
    </table>
@endsection