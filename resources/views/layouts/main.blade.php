<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>@yield('title', 'Challenge System')</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        :root {
            --orange-accent: #FF6B35;
        }
        .navbar {
            background-color: #f8f9fa;
            border-bottom: 3px solid var(--orange-accent);
        }
        .navbar-brand, .nav-link {
            color: #333 !important;
        }
        .navbar-brand:hover, .nav-link:hover {
            color: var(--orange-accent) !important;
        }
        .btn-primary {
            background-color: var(--orange-accent);
            border-color: var(--orange-accent);
        }
        .btn-primary:hover {
            background-color: #e55a2b;
            border-color: #e55a2b;
        }
        footer {
            background-color: #f8f9fa;
            border-top: 3px solid var(--orange-accent);
            padding: 1rem 0;
            margin-top: 2rem;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light mb-4">
        <div class="container">
            <a class="navbar-brand" href="/">Challenge System</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="{{ route('challenges.index') }}">Challenges</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="{{ route('questionsmore.index') }}">Questions</a>
                    </li>
                    <!-- Add more navigation items as needed -->
                </ul>
            </div>
        </div>
    </nav>

    <main class="container">
        @yield('content')
    </main>

    <footer class="text-center">
        <div class="container">
            <span>&copy; {{ date('Y') }} Challenge System. All rights reserved.</span>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>