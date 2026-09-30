interface ICharacter {
    String getDescription();
    int getPowerLevel();
}

class Mario implements ICharacter {
    @Override
    public String getDescription() {
        return "Base Mario";
    }

    @Override
    public int getPowerLevel() {
        return 10;
    }
}

abstract class CharacterDecorator implements ICharacter {
    protected ICharacter wrappedCharacter; // HAS-A relationship

    public CharacterDecorator(ICharacter character) {
        this.wrappedCharacter = character;
    }

    @Override
    public String getDescription() {
        return wrappedCharacter.getDescription(); // Delegates to the inner object
    }

    @Override
    public int getPowerLevel() {
        return wrappedCharacter.getPowerLevel(); // Delegates to the inner object
    }
}

class HeightUpDecorator extends CharacterDecorator { // IS-A relationship
    public HeightUpDecorator(ICharacter character) {
        super(character);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Super Height"; // Enhances the base reply
    }

    @Override
    public int getPowerLevel() {
        return super.getPowerLevel() + 15; // Dynamically increments state
    }
}

class GunPowerDecorator extends CharacterDecorator { // IS-A relationship
    public GunPowerDecorator(ICharacter character) {
        super(character);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Gun Shoot Power"; // Enhances the base reply
    }

    @Override
    public int getPowerLevel() {
        return super.getPowerLevel() + 30; // Dynamically increments state
    }
}

public class DecoratorDemo {
    public static void main(String[] args) {
        // Start with a basic plain Mario
        ICharacter mario = new Mario();
        printStatus(mario);

        // 1st Power Up: Stack Height Up onto Mario
        mario = new HeightUpDecorator(mario);
        printStatus(mario);

        // 2nd Power Up: Stack Gun Power onto the already height-boosted Mario
        mario = new GunPowerDecorator(mario);
        printStatus(mario);

        // Infinite Stacking: Add another Gun Power up recursively!
        mario = new GunPowerDecorator(mario);
        printStatus(mario);
    }

    private static void printStatus(ICharacter character) {
        System.out.println("Character: " + character.getDescription() + " | Total Power: " + character.getPowerLevel());
    }
}
