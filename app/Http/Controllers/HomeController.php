<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Models\Challenge;
use App\Models\User;
use App\Models\School;

class HomeController extends Controller
{
        /**
     * Create a new controller instance.
     *
     * @return void
     */
    // public function __construct()
    // {
    //     $this->middleware('auth');
    // }

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\View\View
     */
    public function index()
    {
        $registered_schools = School::count();
        $active_challenges = Challenge::count();//need to filter active challenges
        $past_challenges = Challenge::count();//need to filter past challenges
        $registered_students = User::count();
        // dd($registered_schools);
        return view('pages.dashboard', compact("registered_schools", "active_challenges", "past_challenges", "registered_students"));
    }
}
