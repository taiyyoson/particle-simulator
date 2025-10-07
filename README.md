# particle-simulator

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
