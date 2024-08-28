import styled from "styled-components";

export const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.85);
  opacity: ${(props) => (props.$isVisible ? "1" : "0")};
  visibility: ${(props) => (props.$isVisible ? "visible" : "hidden")};
  transition: opacity 0.5s ease, visibility 0.5s ease;
  z-index: 1000;
`;
export const ModalOverlay2 = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.85);
  opacity: ${(props) => (props.$isVisible ? "1" : "0")};
  visibility: ${(props) => (props.$isVisible ? "visible" : "hidden")};
  transition: opacity 0.5s ease, visibility 0.5s ease;
  z-index: 1000;
`;

export const ModalHeader = styled.div`
  color: #000000;
  font-family: "Inter", sans-serif;
  font-weight: 600;
  margin-bottom: 10px;
  margin-left: 10px;
  margin-right: 10px;
  font-size: 24px;
  text-align: center;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

export const ModalContent = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  border-radius: 12px;
  border: 20px solid white;
  transform: translate(-50%, -50%);
  background: #f0f0f0;
  border: 5px solid #ffffff;
  padding: 40px;
  border-radius: 12px;
  z-index: 1001;
  width: 80%;
  max-width: 500px;
  box-sizing: border-box;

  @media (max-width: 768px) {
    width: 90%;
  }
`;
export const ModalInputTextHeader = styled.h3`
  font-family: "Inter", sans-serif;
  font-weight: 600;
  font-size: 18px;
  margin: 10px 0 5px;
  text-align: left;
`;

export const ModalInputText = styled.p`
  font-family: "Inter", sans-serif;
  font-size: 14px;
  text-align: justify;
  color: #333;
  margin: 0;
`;

export const CloseButton = styled.span`
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 36px;
  font-weight: bold;
  cursor: pointer;
`;

export const SaveButton = styled.button`
  background: #007bff;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  display: block;
  margin: 20px auto 0;
`;

export const Select = styled.select`
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  font-size: 16px;
  border-radius: 4px;
  border: 1px solid #ccc;
`;
export const ModalInput = styled.input`
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  font-size: 16px;
  border-radius: 4px;
  border: 1px solid #ccc;
  box-sizing: border-box;
`;

export const OptionsList = styled.ul`
  overflow-y: auto;
  position: relative;
  background-color: #fff;
  border-radius: 4px;
  font-family: "Inter", sans-serif;
  font-weight: 400;
  font-size: 16px;
  padding: 0;
  margin: 0;
  margin-top: -10px;
  width: 100%;
  list-style-type: none;
  box-sizing: border-box;
  border: 1px solid #ccc;
  max-height: 190px;
  z-index: 1;
`;

export const OptionItem = styled.li`
  width: 100%;
  padding: 10px;
  font-size: 16px;
  cursor: pointer;
  border-bottom: 1px solid #ccc;
  box-sizing: border-box;
  position: relative;

  &:hover {
    background-color: #f0f0f0;
  }

  &:last-child {
    border-bottom: none;
  }
`;

export const ImageOverlay = styled.img`
  position: absolute;
  z-index: 1002;
`;

export const T1House = styled(ImageOverlay)`
  top: -100px;
  left: -100px;
  width: 250px;
  height: auto;
  @media (max-width: 768px) {
    width: 200px;
    left: -30px;
    top: -75px;
  }
`;

export const T1Computer = styled(ImageOverlay)`
  bottom: -60px;
  right: -80px;
  width: 150px;
  height: auto;
  @media (max-width: 768px) {
    width: 125px;
    right: -30px;
    bottom: -50px;
  }
`;

export const SuccessNotification = styled.div`
  position: fixed;
  top: -500px;
  left: 0;
  width: 100%;
  background-color: #4caf50;
  color: white;
  text-align: left;
  padding: 10px;
  box-sizing: border-box;
  font-family: "Inter", sans-serif;
  font-size: 18px;
  font-weight: 600;
  z-index: 1002;
  transition: top 0.5s ease-in-out;

  &.show {
    top: 0;
  }
`;

export const SuccessNotificationText = styled.p`
  margin: 5px 0 0;
  font-weight: normal;
  font-size: 16px;
  text-align: justify;
`;

export const FailNotification = styled.div`
  position: fixed;
  top: -500px;
  left: 0;
  width: 100%;
  background-color: #da8282;
  color: white;
  text-align: left;
  padding: 10px;
  box-sizing: border-box;
  font-family: "Inter", sans-serif;
  font-size: 18px;
  font-weight: 600;
  z-index: 1002;
  transition: top 0.5s ease-in-out;

  &.show {
    top: 0;
  }
`;

export const FailNotificationText = styled.p`
  margin: 5px 0 0;
  font-weight: normal;
  font-size: 16px;
  text-align: justify;
`;

export const ErrorMessage = styled.p`
  color: red;
  font-family: "Inter", sans-serif;
  font-size: 14px;
  margin-top: 10px;
  text-align: center;
`;
