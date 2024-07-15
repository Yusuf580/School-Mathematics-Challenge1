@extends('layouts.apps')

@section('content')
    <h1>School Details</h1>
    <table class="table table-bordered">
        <tr>
            <th>Name</th>
            <td>{{ $school->Name }}</td>
        </tr>
        <tr>
            <th>Registration</th>
            <td>{{ $school->Registration }}</td>
        </tr>
        <tr>
            <th>District</th>
            <td>{{ $school->District }}</td>
        </tr>
        <tr>
            <th>School Representative</th>
            <td>{{ $school->School_Representative }}</td>
        </tr>
    </table>
    <a href="{{ route('schools.index') }}" class="btn btn-orange">Back to List</a>
@endsection
