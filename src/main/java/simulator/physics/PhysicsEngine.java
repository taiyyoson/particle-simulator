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

    private static final double GRAVITY_Y = -9.8;
    private static final double TIME_STEP = 1.0 / 60.0;

    public PhysicsEngine() {
        this.world = new World<>();
        this.world.setGravity(new Vector2(0, GRAVITY_Y));
        this.particles = new ArrayList<>();
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
