<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateQuestionsMoreTable extends Migration
{
    public function up()
    {
        Schema::create('questionsmore', function (Blueprint $table) {
            $table->string('question_id')->primary();
            $table->string('question');
            $table->string('challenge_title');
            $table->timestamps();
        });
    }

    public function down()
    {
        Schema::dropIfExists('questionsmore');
    }
}