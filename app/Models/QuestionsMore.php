<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class QuestionsMore extends Model
{
    use HasFactory;

    protected $table = 'questionsmore';

    protected $primaryKey = 'question_id';

    public $incrementing = false;

    protected $keyType = 'string';

    protected $fillable = ['question_id', 'question', 'challenge_title'];
}
