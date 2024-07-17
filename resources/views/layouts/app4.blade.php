<!DOCTYPE html>
<html>
<head>
    <title>Ultra Questions</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f0f8ff;
        }
        .navbar {
            background-color: #ff6600;
        }
        .navbar-brand, .navbar-nav .nav-link {
            color: #fff;
        }
        .btn-primary {
            background-color: #ff6600;
            border-color: #ff6600;
        }
        .btn-success {
            background-color: #008000;
            border-color: #008000;
        }
        .table {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light">
        <a class="navbar-brand" href="#">Ultra Questions</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="{{ route('ultraquestions.index') }}">Home</a>
                </li>
            </ul>
        </div>
    </nav>
    <div class="container">
        @yield('content')
    </div>
</body>
</html>
