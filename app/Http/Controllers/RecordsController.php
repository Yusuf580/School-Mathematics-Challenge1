<?php

namespace App\Http\Controllers;

use App\Models\challenge_records;

use Illuminate\Http\Request;

class  RecordsController extends Controller
{

    public function records_view(){
    $records =challenge_records::whereJsonContains('CH001->status','incomplete')
    ->orWhereJsonContains('CH002->status','incomplete')
    ->orWhereJsonContains('CH003->status','incomplete')
    ->orWhereJsonContains('CH004->status','incomplete')
     ->get();
     return view('schools.records_view',compact('records'));
}}
