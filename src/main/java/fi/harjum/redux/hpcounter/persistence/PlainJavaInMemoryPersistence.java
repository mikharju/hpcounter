package fi.harjum.redux.hpcounter.persistence;

import fi.harjum.redux.hpcounter.core.interfaces.ActionResponse;
import fi.harjum.redux.hpcounter.core.interfaces.HpCounterPersistenceInterface;
import fi.harjum.redux.hpcounter.core.interfaces.ResponseFactory;
import fi.harjum.redux.hpcounter.core.interfaces.RpgCharacter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlainJavaInMemoryPersistence implements HpCounterPersistenceInterface {

    private final Map<Integer, RpgCharacter> store = new HashMap<>();
    private int newId = 0;

    @Override
    public RpgCharacter persistNewCharacter(String name, int maxHp) {
        int id = newId++;
        store.put(id, new RpgCharacter(name, maxHp, id, maxHp));
        return getCharacter(id);
    }

    @Override
    public List<RpgCharacter> listCharacters() {
        return store.values().stream().toList();
    }

    @Override
    public ActionResponse updateCharacter(RpgCharacter rpgCharacter) {
        store.put(rpgCharacter.id(), rpgCharacter);
        return ResponseFactory.payload(rpgCharacter, "Persisted character %s", rpgCharacter.toString());
    }

    @Override
    public RpgCharacter getCharacter(int id) {
        return store.get(id);
    }

    @Override
    public ActionResponse deleteCharacter(int id) {
        store.remove(id);
        return ResponseFactory.success("Removed character with id %d", id);
    }
}
