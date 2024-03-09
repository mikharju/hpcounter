package fi.harjum.redux.hpcounter;

import io.micrometer.common.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HpCounterController {

    private Map<Integer, Character> data = null;
    private int newId = 0;


    public void init() {
        if (data == null) {
            data = new HashMap<>();
            newHp(new NewCharacterRequest("Eemo", 74));
            newHp(new NewCharacterRequest("Haldor", 105));
            newHp(new NewCharacterRequest("Santtu", 5));
        }
    }

    @GetMapping()
    public List<Character> getHps() {
        init();
        return data.values().stream().toList();
    }

    @PutMapping("/hp/{id}")
    public ActionResponse updateHp(@RequestBody UpdateHpRequest req, @PathVariable int id) {
        init();
        Character old = data.get(id);
        if (old == null) {
            return new ActionResponse(null, String.format("Character with id %d not found", id), 1);
        }
        int newHp = old.hp() + req.hp();
        data.put(id, new Character(old.name(), Math.min(Math.max(0, newHp), old.maxHp()), id, old.maxHp()));
        return new ActionResponse(String.format("OK %d, updated %s hp to %d", id, old.name(), data.get(id).hp()), null, 0);
    }

    @PutMapping("/character/{id}")
    public ActionResponse updateCharacter(@RequestBody UpdateCharacterRequest req, @PathVariable int id) {
        init();
        Character old = data.get(id);
        if (old == null) {
            return new ActionResponse(null, String.format("Character with id %d not found", id), 1);
        }
        if (StringUtils.isEmpty(req.name())) {
            return new ActionResponse(null, "Name can not be empty", 2);
        }
        data.put(id, new Character(req.name(), old.hp(), id, old.maxHp()));
        return new ActionResponse(String.format("Updated name of character %d from %s to %s", id, old.name(), req.name()), null, 0);
    }

    @PostMapping("/character")
    public ActionResponse newHp(@RequestBody NewCharacterRequest req) {
        int id = newId++;
        data.put(id, new Character(req.name(), req.hp(), id, req.hp()));
        return new ActionResponse("Added new character: " + data.get(id).toString(), null, 0);
    }

    @DeleteMapping("/character/{id}")
    public ActionResponse delete(@PathVariable int id) {
        init();
        Character old = data.get(id);
        if (old == null) {
            return new ActionResponse(null, String.format("Character with id %d not found", id), 1);
        }
        data.remove(id);
        return new ActionResponse(String.format("Deleted character %d %s", id, old.name()), null, 0);
    }
}
