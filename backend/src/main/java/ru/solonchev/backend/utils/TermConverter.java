package ru.solonchev.backend.utils;

public final class TermConverter {

    public static String covertSoftGroupName(String englishTerm) {
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

    public static String convertRoleName(String englishTerm) {
        switch (englishTerm) {
            case "frontend" -> {
                return "Frontend разработчик";
            }
            case "backend" -> {
                return "Backend разработчик";
            }
            case "analysis" -> {
                return "Системный аналитик";
            }
            case "testing" -> {
                return "Ручной тестировщик";
            }
            case "admin" -> {
                return "Прикладной администратор";
            }
        }
        return "Not found";
    }
}
