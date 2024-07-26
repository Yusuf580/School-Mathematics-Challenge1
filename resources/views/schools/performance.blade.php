@extends('layouts.app')

@section('content')
    @include('layouts.navbars.auth.topnav', ['title' => 'Schools Performance'])
    <div class="row mt-4 mx-4">
        <div class="col-12">
           
            <div class="card mb-4">
                <div class="card-header pb-0">
                    <h6>Performance</h6>
                </div>
                <div class="card-body px-0 pt-0 pb-2">
                    <div class="table-responsive p-0">
                        <table class="table align-items-center mb-0">
                            <thead>
                                <tr>
                                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Challenge</th>
                                    <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Best School</th>
                                    <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Worst School</th>
                                   
                                </tr>
                            </thead>
                            <tbody>
                                @foreach($results as $result)
                                    <tr>
                                   <!-- {{  asset($participant->image_url) }} -->
                                        <td>
                                            <div class="d-flex px-3 py-1">
                                               
                                                <div class="d-flex flex-column justify-content-center">
                                                    <h6 class="mb-0 text-sm">{{ $result['challenge'] }}</h6>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                          
                                            <p class="text-sm font-weight-bold mb-0"> 
                                                 @if($result['best'])
                                            {{$result['best']->Name}}
                                            ({{$result['best']->total_score}})
                                            @else No data
                                        @endif
                                        </p>
                                        </td>
                                        <td class="align-middle text-center text-sm">
                                        <p class="text-sm font-weight-bold mb-0">  
                                            @if($result['worst'])
                                            {{$result['worst']->Name}}
                                            ({{$result['worst']->total_score}})
                                            @else No data
                                        @endif
                                        </p>
                                        </td>
                                       
                                    </tr>
                                @endforeach
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection
