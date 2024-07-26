<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class challenge_records extends Model
{
    use HasFactory;
    protected $fillable=['username','CH001','CHOO2','CH003','CH004'];
    protected $casts=['CHOO1'=>'array'
    ,'CHOO2'=>'array','CH003'=>'array','CH004'=>'array'
];
}
