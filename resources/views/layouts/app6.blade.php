@extends('layouts.app')

@section('content')
<style>
    body {
        background-image: url('https://images.unsplash.com/photo-1560785496-3c9d27877182?ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80');
        background-size: cover;
        background-position: center;
        background-attachment: fixed;
        height: 100vh;
    }
    .login-container {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }
    .card-plain {
        background-color: rgba(255, 255, 255, 0.9);
        border: 2px solid #FFA500 !important;
        border-radius: 15px;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
    }
    .btn-primary {
        background-color: #FFA500;
        border-color: #FFA500;
    }
    .btn-primary:hover {
        background-color: #FF8C00;
        border-color: #FF8C00;
    }
    .text-primary {
        color: #FFA500 !important;
    }
</style>

<div class="login-container">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-xl-4 col-lg-5 col-md-7">
                <div class="card card-plain">
                    <div class="card-header pb-0 text-center">
                        <h4 class="font-weight-bolder" style="color: #00008B; text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);">Sign In</h4>
                        <p class="mb-0" style="color:#00008B;">Enter your email and password to sign in</p>
                    </div>
                    <div class="card-body">
                        <form role="form" method="POST" action="{{ route('login.perform') }}">
                            @csrf
                            @method('post')
                            <div class="mb-3">
                                <input type="email" name="email" class="form-control form-control-lg" value="{{ old('email') ?? 'admin@argon.com' }}" aria-label="Email" placeholder="Email">
                                @error('email') <p class="text-danger text-xs pt-1"> {{$message}} </p>@enderror
                            </div>
                            <div class="mb-3">
                                <input type="password" name="password" class="form-control form-control-lg" aria-label="Password" value="secret" placeholder="Password">
                                @error('password') <p class="text-danger text-xs pt-1"> {{$message}} </p>@enderror
                            </div>
                            <div class="form-check form-switch">
                                <input class="form-check-input" name="remember" type="checkbox" id="rememberMe">
                                <label class="form-check-label" for="rememberMe" style="color:#00008B;">Remember me</label>
                            </div>
                            <div class="text-center">
                                <button type="submit" class="btn btn-lg btn-primary btn-lg w-100 mt-4 mb-0">Sign in</button>
                            </div>
                        </form>
                    </div>
                    <div class="card-footer text-center pt-0 px-lg-2 px-1">
                        <p class="mb-1 text-sm mx-auto" style="color:#00008B;">
                            Forgot your password? Reset your password
                            <a href="{{ route('reset-password') }}" class="text-primary font-weight-bold">here</a>
                        </p>
                        <p class="mb-4 text-sm mx-auto" style="color:#00008B;">
                            Don't have an account?
                            <a href="{{ route('register') }}" class="text-primary font-weight-bold">Sign up</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
@endsection