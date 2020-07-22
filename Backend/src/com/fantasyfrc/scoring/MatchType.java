package com.fantasyfrc.scoring;

import com.fantasyfrc.scoring.exceptions.InvalidMatchException;
import com.fantasyfrc.scoring.utils.jsonobjects.match.Match;

public enum MatchType {
    QUAL,
    QUARTER,
    SEMI,
    FINAL,
    EINSTEINRR,
    EINSTEINF;

    static MatchType getMatchType(final Match match) throws InvalidMatchException {
        switch(match.getComp_level()){
            case "qm":
                return MatchType.QUAL;
            case "qf":
                return MatchType.QUARTER;
            case "sf":
                if(match.getKey().contains("cmptx") || match.getKey().contains("cmpmi")){
                    return MatchType.EINSTEINRR;
                }
                return MatchType.SEMI;
            case "f":
                if(match.getKey().contains("cmptx") || match.getKey().contains("cmpmi")){
                    return MatchType.EINSTEINF;
                }
                return MatchType.FINAL;
            default:
                throw new InvalidMatchException();
        }
    }
}