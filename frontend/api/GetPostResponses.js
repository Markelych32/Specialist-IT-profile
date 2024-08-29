import axios from "axios";

export const getGeneralInformation = async (userId) => {
  try {
    const response = await axios.get(
      `/specialist-profile/users/general/${userId}`
    );
    return response.data;
  } catch (error) {
    console.error("Ошибка при получении направлений:", error);
    return [];
  }
};

export const getHardSkillsGeneralInformation = async (userId) => {
  try {
    const response = await axios.get(
      `/specialist-profile/users/working/${userId}`
    );
    return response.data;
  } catch (error) {
    console.error("Ошибка при получении навыков:", error);
    return [];
  }
};

export const getSoftInformation = async (userId) => {
  try {
    const response = await axios.get(
      `/specialist-profile/users/${userId}/marks/soft`
    );
    return response.data;
  } catch (error) {
    console.error("Ошибка при получении навыков:", error);
    return [];
  }
};

export const getHardTable = async (userId) => {
  try {
    const response = await axios.get(
      `/specialist-profile/users/${userId}/marks/hard`
    );
    return response.data;
  } catch (error) {
    console.error("Ошибка при получении навыков:", error);
    return [];
  }
};

export const getHardAddingItems = async (userId) => {
  try {
    const response = await axios.get(
      `/specialist-profile/users/${userId}/marks/add`
    );
    return response.data;
  } catch (error) {
    console.error("Ошибка при получении навыков:", error);
    return [];
  }
};

export const getSuitableCompetences = async (competenceName) => {
  try {
    const response = await axios.post(
      "/specialist-profile/skills/add/suitable",
      {
        competence_name: competenceName,
      }
    );
    return response.data;
  } catch (error) {
    console.error("Ошибка при получении подходящих вариантов:", error);
    return [];
  }
};

export const getSuitableProfiles = async (Name) => {
  try {
    const response = await axios.post(
      "/specialist-profile/users",
      {
        full_name: Name,
      },
      {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Ошибка при получении подходящих вариантов:", error);
    return [];
  }
};

export const updateHardSkill = async (userId, skillId, mark) => {
  try {
    const response = await axios.put(
      `/specialist-profile/users/${userId}/marks/hard`,
      {
        skill_id: skillId,
        mark: mark,
      },
      {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Ошибка при обновлении уровня компетенции:", error);
    throw error;
  }
};

export const updateAddingSkill = async (userId, skillId, mark, roleID) => {
  try {
    const response = await axios.put(
      `/specialist-profile/users/${userId}/marks/add`,
      {
        skill_id: skillId,
        mark: mark,
        new_role_id: roleID,
      },
      {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Ошибка при обновлении уровня компетенции:", error);
    throw error;
  }
};

export const deleteSkill = async (userId, skillId) => {
  try {
    const response = await axios.delete(
      `/specialist-profile/users/${userId}/skills/add/${skillId}`,
      {
        headers: {
          Accept: "application/json",
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Ошибка при удалении навыка:", error);
    throw error;
  }
};

export const updateSoftSkill = async (userId, skillId, mark) => {
  try {
    const response = await axios.put(
      `/specialist-profile/users/${userId}/marks/soft`,
      {
        skill_id: skillId,
        mark: mark,
      },
      {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Ошибка при обновлении уровня компетенции:", error);
    throw error;
  }
};

export const addSkill = async (userId, skillName, roleId) => {
  try {
    const response = await axios.post(
      `/specialist-profile/users/${userId}/skills/add`,
      {
        skill_name: skillName,
        role_id: roleId,
      },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Ошибка при добавлении навыка:", error);
    throw error;
  }
};
