# 🏒 Hockey Manager

## Overview

Hockey Manager is a mix between a turn-based strategy game and a hockey management simulation. Inspired by games like Civilization and Football Manager, it lets you take control of an NHL team and build a dynasty with it's own twist. Currently, the focus is on the local single player experience.

## Features

- TBD

## Prerequisites
- Make sure your current terminal is using Java 21 and Maven is also using Java 21
```bash
mvn --version
java --version
```

- Make sure your Maven packages are clean
```bash
mvn dependency:purge-local-repository -D actTransitively=false -D reResolve=false --fail-at-end
mvn clean install -U
```

## Getting Started

1. **Clone the Repository**
    ```bash
    git clone https://github.com/ludovik/hockey-manager.git
    cd hockey-manager
    ```
2. **Install Dependencies (Windows only)**
    ```bash
    .\install-deps.ps1
    ```
3. **Run the Game**
    ```bash
    npm start
    ```

## Contributing

We welcome contributions! Feel free to open issues, suggest features, or submit pull requests.

## Roadmap

-   Core mechanics: player management, trades, contracts.

-   Turn-based decision-making system.

-   AI-controlled teams with unique strategies.

-   (Future) Multiplayer mode to compete with friends.

## Community & Support

Join the discussion and contribute to the development by following our project on GitHub and Discord.

-   **GitHub Issues** – Report bugs and suggest features.

## License

This project is licensed under the MIT License. See `LICENSE` for details.
