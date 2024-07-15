@extends('layouts.apps')

@section('content')
    <h1>Schools</h1>
    @if(session('success'))
        <div class="alert alert-success">
            {{ session('success') }}
        </div>
    @endif
    <a href="{{ route('schools.create') }}" class="btn btn-orange mb-2">Create School</a>
    <div class="table-responsive">
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Registration</th>
                    <th>District</th>
                    <th>School Representative</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                @foreach($schools as $item)
                    <tr>
                        <td>{{ $loop->iteration }}</td>
                        <td>{{ $item->Name }}</td>
                        <td>{{ $item->Registration }}</td>
                        <td>{{ $item->District }}</td>
                        <td>{{ $item->School_Representative }}</td>
                        <td>
                            <a href="{{ route('schools.show', $item->id) }}" class="btn btn-info">View</a>
                            <a href="{{ route('schools.edit', $item->id) }}" class="btn btn-warning">Edit</a>
                            <form action="{{ route('schools.destroy', $item->id) }}" method="POST" style="display:inline;">
                                @csrf
                                @method('DELETE')
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                @endforeach
            </tbody>
        </table>
    </div>
@endsection
