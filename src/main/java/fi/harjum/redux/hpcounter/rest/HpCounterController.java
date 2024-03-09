package fi.harjum.redux.hpcounter.rest;

import fi.harjum.redux.hpcounter.core.interfaces.*;
import fi.harjum.redux.hpcounter.persistence.PlainJavaInMemoryPersistence;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HpCounterController {

    private final HpCounterCommandInterface cmd;

    public HpCounterController() {
        this.cmd = CoreStart.init(new PlainJavaInMemoryPersistence());
    }

    @GetMapping()
    public List<RpgCharacter> getHps() {
        return cmd.listCharacters();
    }

    @PutMapping("/hp/{id}")
    public ActionResponse updateHp(@RequestBody UpdateHpRequest req, @PathVariable int id) {
        return cmd.incHp(id, req.hp());
    }

    @PutMapping("/character/{id}")
    public ActionResponse updateCharacter(@RequestBody UpdateCharacterRequest req, @PathVariable int id) {
        return cmd.updateCharacter(id, req.name(), req.maxHp());
    }

    @PostMapping("/character")
    public RpgCharacter newCharacter(@RequestBody NewCharacterRequest req) {
        return cmd.newCharacter(req.name(), req.hp());
    }

    @DeleteMapping("/character/{id}")
    public ActionResponse delete(@PathVariable int id) {
        return cmd.deleteCharacter(id);
    }
}
