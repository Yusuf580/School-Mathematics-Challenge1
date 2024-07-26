@extends('layouts.app5')

@section('content')
    <h1>Create School</h1>
    <form action="{{ route('schools.store') }}" method="POST">
        @csrf
        <div class="form-group">
            <label for="Name">School Name:</label>
            <input type="text" id="Name" name="Name" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="Registration">Registration Number:</label>
            <input type="text" id="Registration" name="Registration" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="District">District:</label>
            <input type="text" id="District" name="District" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="School_Representative">School Representative:</label>
            <input type="text" id="School_Representative" name="School_Representative" class="form-control" required>
        </div>
       <!-- <div class="form-group">
            <label for="Representative_email"> Representative email:</label>
            <input type="text" id="Representative_email" name="Representative_email" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="Representative_password"> Representative Password:</label>
            <input type="password" id="Representative_password" name="Representative_password" class="form-control" required>
        </div> -->
        <button type="submit" class="btn btn-orange">Submit</button>
    </form>
@endsection