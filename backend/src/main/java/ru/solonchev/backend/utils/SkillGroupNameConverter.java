package ru.solonchev.backend.utils;

public final class SkillGroupNameConverter {

    public static String covertFromEnglishTermToRussian(String englishTerm) {
        switch (englishTerm) {
            case "thinking" -> {
                return "Мышление";
            }
            case "personal_effectiveness" -> {
                return "Личная эффективность";
            }
            case "communication" -> {
                return "Коммуникация";
            }
            case "management_skills" -> {
                return "Управленческие навыки";
            }
        }
        return "Not found";
    }
}
