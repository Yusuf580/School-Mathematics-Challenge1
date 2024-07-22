<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;


class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        DB::table('users')->insert([
            'username' => 'Herman',
            'firstname' => 'Admin',
            'lastname' => 'Admin',
            'email' => 'hermanssenoga@gmail.com',
            'password' => bcrypt('Herman203')
        ]);
    }
}
