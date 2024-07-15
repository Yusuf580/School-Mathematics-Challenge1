@extends('layouts.apps')

@section('content')
    <h1>Edit School</h1>
    <form action="{{ route('schools.update', $school->id) }}" method="POST">
        @csrf
        @method('put')
        <div class="form-group">
            <label for="Name">School Name:</label>
            <input type="text" id="Name" name="Name" class="form-control" value="{{ $school->Name }}" required>
        </div>
        <div class="form-group">
            <label for="Registration">Registration Number:</label>
            <input type="text" id="Registration" name="Registration" class="form-control" value="{{ $school->Registration }}" required>
        </div>
        <div class="form-group">
            <label for="District">District:</label>
            <input type="text" id="District" name="District" class="form-control" value="{{ $school->District }}" required>
        </div>
        <div class="form-group">
            <label for="School_Representative">School Representative:</label>
            <input type="text" id="School_Representative" name="School_Representative" class="form-control" value="{{ $school->School_Representative }}" required>
        </div>
        <button type="submit" class="btn btn-orange">Update</button>
    </form>
@endsection