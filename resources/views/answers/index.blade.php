

@extends('layouts.app5')

@section('content')
<div class="container">
    <h1>Answers</h1>
    <form action="{{ route('answers.import') }}" method="POST" enctype="multipart/form-data">
        @csrf
        <div class="form-group">
            <label for="file">Import Answers</label>
            <input type="file" name="file" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Import</button>
    </form>
    
    <table class="table mt-4">
        <thead>
            <tr>
                <th>Answer ID</th>
                <th>Answer</th>
                <th>Question ID</th>
            </tr>
        </thead>
        <tbody>
            @foreach($answers as $answer)
                <tr>
                    <td>{{ $answer->answer_id }}</td>
                    <td>{{ $answer->answer }}</td>
                    <td>{{ $answer->question_id }}</td>
                </tr>
            @endforeach
        </tbody>
    </table>
</div>
@endsection
