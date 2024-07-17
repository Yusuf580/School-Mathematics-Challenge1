@extends('layouts.app4')

@section('content')
<div class="container">
    <h2>Questions List</h2>
    @if (session('success'))
        <div class="alert alert-success">
            {{ session('success') }}
        </div>
    @endif

    <form action="{{ route('questionsmore.import') }}" method="POST" enctype="multipart/form-data">
        @csrf
        <div class="form-group">
            <label for="file">Upload Excel File</label>
            <input type="file" name="file" class="form-control">
        </div>
        <button type="submit" class="btn btn-primary mt-2">Import</button>
    </form>

    <table class="table table-bordered mt-4">
        <thead>
            <tr>
                <th>Question ID</th>
                <th>Question</th>
                <th>Challenge Title</th>
            </tr>
        </thead>
        <tbody>
            @foreach ($questions as $question)
            <tr>
                <td>{{ $question->question_id }}</td>
                <td>{{ $question->question }}</td>
                <td>{{ $question->challenge_title }}</td>
            </tr>
            @endforeach
        </tbody>
    </table>
</div>
@endsection
