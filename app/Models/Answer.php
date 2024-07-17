<?php
// app/Models/Answer.php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Answer extends Model
{
    use HasFactory;

    protected $primaryKey = 'answer_id';
    public $incrementing = false;
    protected $keyType = 'string';

    protected $fillable = [
        'answer_id',
        'answer',
        'question_id',
    ];
}
