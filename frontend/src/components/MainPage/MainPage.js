import React, { useEffect, useState } from "react";
import styled from "styled-components";
import ButtonPanel from "./ButtonPanel/ButtonPanel";
import ProfileContainer from "./ProfileContainer/ProfileContainer";
import {
  getGeneralInformation,
  getHardSkillsGeneralInformation,
} from "../../../api/GetPostResponses";

import {
  SkeletonContainer,
  SkeletonSpecification,
  SkeletonProfile,
  SkeletonProfileContainer,
} from "../common/Sceleton/Sceleton";

const MainPage = () => {
  const [loading, setLoading] = useState(true);
  const [loadingGeneral, setLoadingGeneral] = useState(true);
  const [loadingMain, setLoadingMain] = useState(true);
  const [userData, setUserData] = useState({
    avatar: "",
    name: "",
    birthDate: "",
    gender: "",
    location: "",
  });

  const [specificationsData, setSpecificationsData] = useState([
    { title: "", text: "" },
    { title: "", text: "" },
    { title: "", text: "" },
  ]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const generalInfo = await getGeneralInformation();
        setUserData({
          name: `${generalInfo.first_name} ${generalInfo.last_name}`,
          birthDate: `${generalInfo.date_of_birth} / ${generalInfo.years}`,
          gender: generalInfo.gender,
          location: generalInfo.location,
        });
      } catch (error) {
        console.error("Ошибка при получении направлений:", error);
      } finally {
        setLoadingGeneral(false);
      }
    };

    const fetchHardSkills = async () => {
      try {
        const hardSkillsInfo = await getHardSkillsGeneralInformation(1);
        setSpecificationsData([
          { title: hardSkillsInfo.post, text: "Должность" },
          { title: hardSkillsInfo.role, text: "Роль" },
          { title: hardSkillsInfo.specialization, text: "Специализация" },
        ]);
      } catch (error) {
        console.error("Ошибка при получении навыков:", error);
      } finally {
        setLoadingMain(false);
      }
    };

    fetchData();
    fetchHardSkills();
  }, []);

  useEffect(() => {
    if (!loadingGeneral && !loadingMain) {
      setLoading(false);
    }
  }, [loadingGeneral, loadingMain]);

  return (
    <Container>
      <ButtonPanel />
      {loading ? (
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
        />
      )}
    </Container>
  );
};

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 50px;
`;

export default MainPage;
