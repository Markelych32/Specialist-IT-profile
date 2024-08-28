const getIconForMark = (mark) => {
  switch (mark) {
    case 3:
      return "③";
    case 2:
      return "②";
    case 1:
      return "①";
    case 0:
      return "⓪";
    case -1:
      return "❗";
    default:
      return "";
  }
};

export default getIconForMark;
