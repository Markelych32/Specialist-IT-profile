import React, { useEffect, useState } from "react";
import styled from "styled-components";
import ButtonPanel from "./ButtonPanel/ButtonPanel";
import ProfileContainer from "./AProfileContainer/ProfileContainer";
import SearchPanel from "./SearchPanel/SearchPanel";
import {
  getGeneralInformation,
  getHardSkillsGeneralInformation,
  getSuitableProfiles,
} from "@api/GetPostResponses";
import {
  SkeletonContainer,
  SkeletonSpecification,
  SkeletonProfile,
  SkeletonProfileContainer,
} from "@common/Sceleton/Sceleton";

const AdminPage = () => {
  const [loading, setLoading] = useState(false);
  const [searchQuery, setSearchQuery] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [userIdNumber, setUserIdNumber] = useState(-1);
  const [userSelected, setUserSelected] = useState(false);
  const [userData, setUserData] = useState({
    name: "",
    birthDate: "",
    gender: "",
    location: "",
  });
  const [specificationsData, setSpecificationsData] = useState([
    { title: "Не указано", text: "Должность" },
    { title: "Не указано", text: "Роль" },
    { title: "Не указано", text: "Специализация" },
  ]);
  const [searching, setSearching] = useState(false);
  const [hasSearched, setHasSearched] = useState(false);

  useEffect(() => {
    const delayDebounceFn = setTimeout(() => {
      if (searchQuery) {
        searchUsers(searchQuery);
      } else {
        setSearchResults([]);
        setSearching(false);
        setHasSearched(false);
      }
    }, 1000);

    return () => clearTimeout(delayDebounceFn);
  }, [searchQuery]);

  const searchUsers = async (query) => {
    setSearching(true);
    setHasSearched(true);
    try {
      const response = await getSuitableProfiles(query);
      if (response && response.length > 0) {
        setSearchResults(response);
        setUserSelected(false);
      } else {
        setSearchResults([]);
      }
    } catch (error) {
      console.error("Ошибка при поиске пользователей:", error);
      setSearchResults([]);
    } finally {
      setSearching(false);
    }
  };

  const fetchGeneralInformation = async (userId) => {
    try {
      const response = await getGeneralInformation(userId);
      const { first_name, last_name, date_of_birth, years, gender, location } =
        response || {};
      setUserData({
        name: `${first_name || "Не указано"} ${last_name || "Не указано"}`,
        birthDate: date_of_birth
          ? `${date_of_birth} / ${years || "Не указано"}`
          : "Не указана",
        gender: gender || "Не указано",
        location: location || "Не указано",
      });
    } catch (error) {
      console.error("Ошибка при получении общей информации:", error);
    }
  };

  const fetchHardSkillsInformation = async (userId) => {
    try {
      const response = await getHardSkillsGeneralInformation(userId);
      const {
        post = "Не указано",
        role = "Не указано",
        specialization = "Не указано",
      } = response || {};
      setSpecificationsData([
        { title: post, text: "Должность" },
        { title: role, text: "Роль" },
        { title: specialization, text: "Специализация" },
      ]);
    } catch (error) {
      console.error("Ошибка при получении информации о навыках:", error);
    }
  };

  const fetchUserData = async (userId) => {
    setLoading(true);
    setUserSelected(true);
    setUserIdNumber(userId);
    try {
      await fetchGeneralInformation(userId);
      await fetchHardSkillsInformation(userId);
    } catch (error) {
      console.error("Ошибка при получении данных пользователя:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearchInputChange = (e) => {
    setSearchQuery(e.target.value);
  };

  const handleUserSelect = (userId) => {
    fetchUserData(userId);
    setSearchQuery("");
  };

  return (
    <Container>
      <ButtonPanel />

      <SearchPanel
        placeholder="Введите Фамилию или Имя"
        value={searchQuery}
        onChange={handleSearchInputChange}
      />
      {searching && !userSelected && <Message>Поиск данных...</Message>}
      {!searching &&
        hasSearched &&
        searchResults.length === 0 &&
        !userSelected && <Message>Пользователь не найден</Message>}
      {searchResults.length > 0 && !userSelected && !searching && (
        <SearchResultsContainer>
          {searchResults.map((user) => (
            <SearchResultItem
              key={user.id}
              onClick={() => handleUserSelect(user.id)}
            >
              {user.last_name} {user.first_name}, {user.date_of_birth}
            </SearchResultItem>
          ))}
        </SearchResultsContainer>
      )}
      {userSelected &&
        (loading ? (
          <SkeletonContainer>
            <SkeletonProfileContainer>
              <SkeletonSpecification />
              <SkeletonSpecification />
            </SkeletonProfileContainer>
            <SkeletonProfile />
          </SkeletonContainer>
        ) : (
          <ProfileContainer
            userData={userData}
            specificationsData={specificationsData}
            userIdNumber={userIdNumber}
          />
        ))}
    </Container>
  );
};

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 50px;
`;

const SearchResultsContainer = styled.div`
  overflow-y: auto;
  position: relative;
  background-color: #fff;
  border-radius: 4px;
  font-family: "Inter", sans-serif;
  font-weight: 400;
  font-size: 16px;
  padding: 0;
  margin: 0;
  margin-top: -30px;
  width: 100%;
  list-style-type: none;
  box-sizing: border-box;
  border: 1px solid #ccc;
  max-width: 500px;
  max-height: 1000px;
  z-index: 1;
`;

const SearchResultItem = styled.div`
  padding: 10px;
  cursor: pointer;
  font-family: "SansSerif", sans-serif;
  font-size: 16px;
  font-weight: 400;
  &:hover {
    background-color: #f0f0f0;
  }
`;

const Message = styled.div`
  margin-top: 10px;
  font-size: 18px;
  color: #777;
`;

export default AdminPage;
