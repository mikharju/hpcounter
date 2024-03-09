package fi.harjum.redux.hpcounter.rest;

import fi.harjum.redux.hpcounter.core.interfaces.*;
import fi.harjum.redux.hpcounter.persistence.PlainJavaInMemoryPersistence;
import io.micrometer.common.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HpCounterController {

    private HpCounterCommandInterface cmd;
    private int newId = 0;

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
        return cmd.newCharacter(req);
    }

    @DeleteMapping("/character/{id}")
    public ActionResponse delete(@PathVariable int id) {
        return cmd.deleteCharacter(id);
    }
}
