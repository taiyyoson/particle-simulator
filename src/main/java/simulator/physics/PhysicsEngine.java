package simulator.physics;

import org.dyn4j.world.World;
import org.dyn4j.geometry.Vector2;
import simulator.models.Particle;

import java.util.ArrayList;
import java.util.List;

public class PhysicsEngine {
    private World<org.dyn4j.dynamics.Body> world;
    private List<Particle> particles;
    private boolean running;

    private double TIME_STEP = 1.0 / 60.0;

    public static class Builder {
        private double GRAVITY_X = 0;
        private double GRAVITY_Y = -9.8;
        private double TIME_STEP = 1.0 / 60.0;

        public void setGravityX(double GRAVITY_X) {
            this.GRAVITY_X = GRAVITY_X;
        }

        public void setGravityY(double GRAVITY_Y) {
            this.GRAVITY_Y = GRAVITY_Y;
        }

        public void setTimeStep(double TIME_STEP) {
            this.TIME_STEP = TIME_STEP;
        }

        public PhysicsEngine build() {
            assert 0 < TIME_STEP;
            return new PhysicsEngine(GRAVITY_X, GRAVITY_Y, TIME_STEP);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private PhysicsEngine(double GRAVITY_X, double GRAVITY_Y, double TIME_STEP) {
        this.TIME_STEP = TIME_STEP;
        this.world = new World();
        this.world.setGravity(new Vector2(0, GRAVITY_Y));
        this.particles = new ArrayList();
        this.running = false;
    }

    public void addParticle(Particle particle) {
        particles.add(particle);
        world.addBody(particle.getBody());
    }

    public void removeParticle(Particle particle) {
        particles.remove(particle);
        world.removeBody(particle.getBody());
    }

    public void update() {
        if (!running) return;
        world.update(TIME_STEP);
        updateParticleStates();
    }

    private void updateParticleStates() {
        for (Particle particle : particles) {
            particle.syncFromBody();
        }
    }

    public void start() {
        this.running = true;
    }

    public void pause() {
        this.running = false;
    }

    public void resume() {
        this.running = true;
    }

    public void reset() {
        this.running = false;
        for (Particle particle : new ArrayList<>(particles)) {
            removeParticle(particle);
        }
    }

    public World<org.dyn4j.dynamics.Body> getWorld() { return world; }
    public List<Particle> getParticles() { return particles; }
    public boolean isRunning() { return running; }
    public void setGravity(double x, double y) {
        world.setGravity(new Vector2(x, y));
    }
}
