package net.worldseed.particleemitter.misc;

import com.google.gson.JsonElement;
import net.worldseed.particleemitter.particle.ParticleAppearanceTinting;
import net.worldseed.particleemitter.runtime.ParticleEmitterScript;
import net.worldseed.particleemitter.runtime.ParticleInterface;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public record Colour(ParticleEmitterScript r, ParticleEmitterScript g, ParticleEmitterScript b) {
    public Colour(String r, String g, String b) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this(ParticleEmitterScript.fromString(r), ParticleEmitterScript.fromString(g), ParticleEmitterScript.fromString(b));
    }

    public ParticleAppearanceTinting.ColourEvaluated toColourEvaluated(ParticleInterface particle) {
        return new ParticleAppearanceTinting.ColourEvaluated(r.evaluate(particle), g.evaluate(particle), b.evaluate(particle));
    }

    public Colour(double v, double v1, double v2) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this(ParticleEmitterScript.fromDouble(v), ParticleEmitterScript.fromDouble(v1), ParticleEmitterScript.fromDouble(v2));
    }

    public static Colour fromJson(JsonElement color) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (color.isJsonPrimitive()) {
            var s = color.getAsString();

            if (s.length() == 9 && s.startsWith("#")) s = s.substring(1);
            if (s.length() == 8) s = s.substring(2);
            if (s.length() == 6) s = "#" + s;

            var c = Color.decode(s);
            return new Colour(c.getRed() / 255.0, c.getGreen() / 255.0, c.getBlue() / 255.0);
        } else {
            String r = color.getAsJsonArray().get(0).getAsString();
            String g = color.getAsJsonArray().get(1).getAsString();
            String b = color.getAsJsonArray().get(2).getAsString();
            return new Colour(r, g, b);
        }
    }
}
