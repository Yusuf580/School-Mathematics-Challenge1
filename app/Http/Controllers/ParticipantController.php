<?php

namespace App\Http\Controllers;

use App\Models\participant;

use Illuminate\Http\Request;


class ParticipantController extends Controller
{
    public function participant()
    {
        $participant=participant::all();
       // $schools = School::all();
        return view('schools.participant',compact('participant'));
    }


}

