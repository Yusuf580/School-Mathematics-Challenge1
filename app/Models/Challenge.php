<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Challenge extends Model
{
    use HasFactory;

    protected $primaryKey = 'title';
    public $incrementing = false;
    protected $keyType = 'string';

    protected $fillable = [
        'title',
        'start_date',
        'end_date',
        'duration',
        'question_count',
        'is_active',
    ];


    /**
     * The attributes that should be cast to native types.
     *
     * @var array
     */
    protected $casts = [
        'start_date' => 'datetime',
        'end_date' => 'datetime',
        'is_active' => 'boolean',
    ];
}
