import React from "react";
import styled from "styled-components";

const Container = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f0f0f0;
`;

const TextContainer = styled.div`
  flex: 1;
  max-width: 500px;
  text-align: left;
`;

const Title = styled.h1`
  font-size: 48px;
  font-weight: bold;
  margin: 0;
`;

const Subtitle = styled.p`
  font-size: 24px;
  margin-top: 10px;
`;

const ImageContainer = styled.div`
  flex: 1;
  max-width: 500px;
  display: flex;
  justify-content: center;
`;

const Image = styled.img`
  max-width: 100%;
  height: auto;
`;

const NotFoundPage = () => {
  return (
    <Container>
      <TextContainer>
        <Title>Упс!</Title>
        <Subtitle>Страница не найдена</Subtitle>
      </TextContainer>
      <ImageContainer>
        <Image
          src="https://uploads.networkly.app/community_avatars/cache/to_webp_community_ava/medium-e89e1a2fc9f27d2be733776df9afc712-65bab41f871c1491550884.png"
          alt="404 Image"
        />
      </ImageContainer>
    </Container>
  );
};

export default NotFoundPage;
