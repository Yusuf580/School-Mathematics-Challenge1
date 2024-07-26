<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    
    public function up(): void
    {
        
        Schema::create('challenge_records', function (Blueprint $table) {
        
           $table->string('username')->unique();
            $table->string('registrationNumber');
            $table->json('CH001')->nullable();
            $table->json('CH002')->nullable();
            $table->json('CH003')->nullable();
            $table->json('CH004')->nullable();


        });
    }

    

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('challenge_records');
    }
};
