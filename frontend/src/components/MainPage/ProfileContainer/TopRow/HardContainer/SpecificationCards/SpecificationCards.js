import React from "react";
import styled from "styled-components";

const SpecificationCardContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  justify-content: center;
  align-items: center;
  flex-grow: 1;
  margin: 0px 10px 0;
`;

const SpecificationCard = styled.div`
  background-color: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  flex: 1;
  min-width: 200px;
  max-width: 300px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 180px;
  box-sizing: border-box;
  @media (max-width: 1024px) {
    max-width: 100%;
  }
`;

const SpecificationCardTitle = styled.div`
  font-family: "Inter", sans-serif;
  font-weight: 600;
  font-size: 24px;
  text-align: center;
  margin-top: auto;
  margin-bottom: auto;
  @media (max-width: 1225px) {
    font-size: 21px;
  }
`;

const SpecificationCardText = styled.div`
  font-family: "Inter", sans-serif;
  font-weight: 400;
  font-size: 20px;
  color: #777777;
  text-align: center;
  @media (max-width: 1225px) {
    font-size: 18px;
  }
`;

const SpecificationCards = React.memo(({ specificationsData }) => {
  return (
    <SpecificationCardContainer>
      {specificationsData.map((spec, index) => (
        <SpecificationCard key={index}>
          <SpecificationCardTitle>{spec.title}</SpecificationCardTitle>
          <SpecificationCardText>{spec.text}</SpecificationCardText>
        </SpecificationCard>
      ))}
    </SpecificationCardContainer>
  );
});

export default SpecificationCards;
