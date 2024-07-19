<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>{{ config('app.name', 'Laravel') }}</title>
    
    <!-- Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@400;600;700&display=swap" rel="stylesheet">
    
    <!-- Styles -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Nunito', sans-serif;
            background-color: #f0f8f0; /* Light green background */
        }
        .navbar {
            background-color: #ff8c00; /* Dark orange */
        }
        .navbar-brand, .nav-link {
            color: #ffffff !important;
        }
        .btn-orange {
            background-color: #ffa500; /* Orange */
            border-color: #ffa500;
            color: #ffffff;
        }
        .btn-orange:hover {
            background-color: #ff8c00; /* Dark orange */
            border-color: #ff8c00;
            color: #ffffff;
        }
        .card {
            border-color: #4caf50; /* Green */
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .card-header {
            background-color: #4caf50; /* Green */
            color: #ffffff;
        }
        .table {
            background-color: #ffffff;
        }
        .table thead {
            background-color: #ffa500; /* Orange */
            color: #ffffff;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-md navbar-dark mb-4">
        <div class="container">
            <a class="navbar-brand" href="{{ url('/') }}">
                {{ config('app.name', 'Laravel') }}
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="{{ __('Toggle navigation') }}">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <!-- Left Side Of Navbar -->
                <ul class="navbar-nav me-auto">
                    <!-- Add your menu items here -->
                </ul>

                <!-- Right Side Of Navbar -->
                <ul class="navbar-nav ms-auto">
                    <!-- Add authentication links here if needed -->
                </ul>
            </div>
        </div>
    </nav>

    <main class="container">
        <div class="card">
            <div class="card-header">
                <h2>@yield('title', 'Welcome')</h2>
            </div>
            <div class="card-body">
                @yield('content')
            </div>
        </div>
    </main>

    <footer class="mt-4 text-center text-muted">
        <p>&copy; {{ date('Y') }} {{ config('app.name', 'Laravel') }}. All rights reserved.</p>
    </footer>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>