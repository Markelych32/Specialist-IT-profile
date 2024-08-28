import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import MainPage from "./MainPage/MainPage";
import AdminPage from "./AdminPage/AdminPage";
import NotFoundPage from "./ErrorPage/ErrorPage";

class App extends React.Component {
  render() {
    return (
      <Router>
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/admin" element={<AdminPage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </Router>
    );
  }
}

export default App;
