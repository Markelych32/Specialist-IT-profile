import React from "react";
import styled from "styled-components";
import TopRow from "./TopRow/TopRow";
import SoftContainer from "./SoftContainer/SoftContainer";

const ContentContainer = styled.div`
  width: 97%;
  margin: 20px auto;
  max-width: 1500px;
  background-color: #eef1f6;
  border-radius: 12px;
  padding: 20px;
  box-sizing: border-box;
`;

const ProfileContainer = ({ userData, specificationsData }) => {
  return (
    <ContentContainer>
      <TopRow userData={userData} specificationsData={specificationsData} />
      <SoftContainer />
    </ContentContainer>
  );
};

export default ProfileContainer;
