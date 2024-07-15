<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Question extends Model
{
    use HasFactory;

    protected $fillable = [
        'question',
        'challenge_title',
    ];

    public function challenge()
    {
        return $this->belongsTo(Challenge::class, 'challenge_title', 'title');
    }
}
