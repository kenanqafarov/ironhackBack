package com.example.demo.controller;

import com.example.demo.dto.BattleRequest;
import com.example.demo.model.Attacker;
import com.example.demo.model.Character;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/battle")
@CrossOrigin(origins = "http://localhost:5173")
public class BattleController {

    @PostMapping("/simulate")
    public Map<String, Object> simulate(@RequestBody BattleRequest request) {
        Character p1 = createCharacter(request.getPlayer1());
        Character p2 = createCharacter(request.getPlayer2());

        List<Map<String, Object>> detailedLogs = new ArrayList<>();
        int round = 1;

        while (p1.getHp() > 0 && p2.getHp() > 0 && round <= 100) {
            Map<String, Object> roundData = new LinkedHashMap<>();
            roundData.put("roundId", round);

            // Player 1 attack
            roundData.put("player1", buildAttackInfo(p1, p2));

            // Player 2 attack (if still alive)
            Map<String, Object> p2Attack = null;
            if (p2.getHp() > 0) {
                p2Attack = buildAttackInfo(p2, p1);
            }
            roundData.put("player2", p2Attack);

            // HP after round
            Map<String, Integer> hpAfter = new LinkedHashMap<>();
            hpAfter.put("player1", p1.getHp());
            hpAfter.put("player2", p2.getHp());
            roundData.put("hpAfter", hpAfter);

            detailedLogs.add(roundData);
            round++;
        }

        String winner = "Draw";
        boolean isDraw = true;

        if (p1.getHp() > 0 && p2.getHp() <= 0) {
            winner = p1.getName();
            isDraw = false;
        } else if (p2.getHp() > 0 && p1.getHp() <= 0) {
            winner = p2.getName();
            isDraw = false;
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("roundCount", round - 1);
        response.put("winner", winner);
        response.put("isDraw", isDraw);
        response.put("detailedLogs", detailedLogs);

        return response;
    }

    private Map<String, Object> buildAttackInfo(Character attacker, Character target) {
        String log = ((Attacker) attacker).attack(target);

        Map<String, Object> info = new LinkedHashMap<>();
        info.put("name", attacker.getName());

        String type = "Unknown";
        String emoji = "";
        int damage = 0;

        // Warrior attacks
        if (log.contains("HEAVY ATTACK")) {
            type = "HEAVY ATTACK";
            emoji = "💥";
        } else if (log.contains("WEAK ATTACK")) {
            type = "WEAK ATTACK";
            emoji = "⚔️";
        } else if (log.contains("exhausted")) {
            type = "EXHAUSTED";
            emoji = "😴";
        }

        // Wizard attacks
        else if (log.contains("FIREBALL")) {
            type = "FIREBALL";
            emoji = "🔥";
        } else if (log.contains("STAFF")) {
            type = "STAFF STRIKE";
            emoji = "🪄";
        } else if (log.contains("out of mana")) {
            type = "OUT OF MANA";
            emoji = "🧘";
        }

        // Parse damage
        try {
            int start = log.lastIndexOf("Dealt ") + 6;
            int end = log.indexOf(" damage", start);
            if (start >= 6 && end > start) {
                String damageStr = log.substring(start, end).trim();
                damage = Integer.parseInt(damageStr);
            }
        } catch (Exception ignored) {
            // damage remains 0
        }

        info.put("attackType", type);
        info.put("emoji", emoji);
        info.put("damage", damage);
        info.put("description", log);

        return info;
    }

    private Character createCharacter(BattleRequest.CharacterDto dto) {
        if ("warrior".equalsIgnoreCase(dto.getType())) {
            return new com.example.demo.model.Warrior(
                    dto.getName(),
                    dto.getHp(),
                    dto.getAttr1(),   // stamina
                    dto.getAttr2()    // strength
            );
        } else {
            return new com.example.demo.model.Wizard(
                    dto.getName(),
                    dto.getHp(),
                    dto.getAttr1(),   // mana
                    dto.getAttr2()    // intelligence
            );
        }
    }
}