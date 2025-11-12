# particle-simulator

## Local Development Setup

### Prerequisites

1. **Java 17** - Required for JavaFX app and Spring Boot backend
2. **Maven** - Build tool for both services
3. **Docker** - For running MongoDB and services in containers

### Docker Setup (without Docker Desktop)

This project uses **Colima** as a lightweight Docker alternative:

```bash
# Install Colima (if not already installed)
brew install colima

# Start Colima
colima start

# Verify Docker is running
docker ps
```

### Running the Services

#### 1. Start MongoDB

```bash
# Start MongoDB container
docker-compose up -d mongodb

# Verify MongoDB is running
docker ps
```

Expected output: You should see `particle-sim-mongodb` container running on port 27017.

#### 2. Build and Run Spring Boot Experiment Service

```bash
# Navigate to experiment service
cd experiment-service

# Build the service (Note: Uses Java 22 without Lombok)
mvn clean package -DskipTests

# Run the service
mvn spring-boot:run
```

Expected output: Service should start on port 8080 with message `Tomcat started on port 8080 (http)`.

#### 3. Test the Backend API

In a new terminal:

```bash
# Test POST - Create an experiment
curl -X POST http://localhost:8080/experiments \
  -H "Content-Type: application/json" \
  -d '{"engineType":"DYNB4J","particleCount":50,"avgFPS":60.0,"computeTimeMs":100}'

# Test GET - Retrieve all experiments
curl http://localhost:8080/experiments
```

Expected output: You should see JSON responses with experiment data including auto-generated ID and timestamp.

---

## Project (Refined) Scope

### Core Features (In Scope)
- **2D Particle Simulation**
  - Add, remove, and modify particles in real-time
  - Support for multiple particle types (mass, charge, size)
  - Mouse interactions: drag particles, pan viewport, zoom in/out

- **Physics Engine (Dyn4j Integration)**
  - Gravitational forces between particles
  - Spring/constraint systems for linked particles
  - Collision detection and response
  - Configurable physics parameters (gravity constant, damping, time-step)
  - Starting with Dyn4J engine, implementing from scratch may be in scope past Oct 7 Deliverable

- **Rendering Engine (JavaFX)**
  - Real-time 2D visualization on Canvas
  - Visual feedback for forces and constraints
  - Smooth 60 FPS rendering with interpolation

- **Interactive Frontend (JavaFX UI)**
  - Control panel for physics parameter adjustments
  - Simulation controls: pause, resume, reset, step-through
  - Preset selector for predefined scenarios
  - Mouse-driven particle manipulation

- **Data Persistence (MongoDB)**
  - Save/load simulation states
  - Store predefined presets (solar system, particle swarm, etc.)
  - Log simulation metrics for analysis

- **Deployment Infrastructure**
  - Docker containerization for application and database
  - GCP deployment with Docker Compose orchestration

### Out of Scope (Future Enhancements)
- 3D rendering and physics simulation
- Advanced particle types (fluid dynamics, soft bodies)
- Multiplayer/collaborative simulations
- GPU-accelerated physics calculations
- Mobile or web-based frontends

### October 7 Milestone Deliverables
- Basic JavaFX window rendering 50-100 particles as circles
- Dyn4j integrated with gravity and spring forces functional
- Simulation loop: physics update → render cycle at stable FPS
- Simple UI: pause/resume button and gravity slider
- **All backend-focused; polished UI is deferred**
